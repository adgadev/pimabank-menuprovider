package com.grexdev.pimabank.menuprovider.parser.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.grexdev.pimabank.menuprovider.parser.RegexParserMenuProviderConfiguration;
import com.grexdev.pimabank.menuprovider.parser.regex.NamedGroupRegistry.GroupDetails;
import com.grexdev.pimabank.menuprovider.parser.regex.NamedGroupRegistry.GroupType;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RegexGroupExtractor {

    private final RegexParserMenuProviderConfiguration configuration;
    
    private final NamedGroupRegistry registry;
    
    private final ExecutorService extractorThreadPool;
    
    public Object extractRegexGroupsValues(final String content) throws InterruptedException, ExecutionException, TimeoutException {
        log.debug("Extracting regexp groups from content >>>{}<<<", content);
    
        // Extracting regexp group is executed in separate thread to allow timeouting this searching if it takes too much time.
        // (string in which we're searching regexp groups is wrapped with InterruptableCharSequence and working thread may be interruprted by Thread.interrupt() method invoked implicitly from Future object
        
        Callable<Object> task = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return processGroup(registry.getRootGroupDetails(), content);
            }
        };
        
        Future<Object> regexpExtractingTask = extractorThreadPool.submit(task);
        Object rootGroupValue = regexpExtractingTask.get(configuration.getRegexpMatcherTimeout(), TimeUnit.MILLISECONDS);
        log.debug("Root group value >>>{}<<<", rootGroupValue);

        return rootGroupValue;
    }

    private Object processGroup(GroupDetails parentGroupDetails, String content) {
        log.debug("Processing group {}", parentGroupDetails);
        //log.debug("Group content = >>>{}<<<", content);
        
        Matcher matcher = getMatcher(parentGroupDetails, content);
        
        if (parentGroupDetails.isType(GroupType.BLOCK)) {
            List<Object> groups = new ArrayList<Object>();
            
            while (matcher.find()) {
                groups.add(processChildGroups(matcher, parentGroupDetails));
            }
            return groups;
            
        } else {
            if (matcher.matches()) {
                return processChildGroups(matcher, parentGroupDetails);
            } else {
                throw new RuntimeException("Pattern not matched : " + parentGroupDetails);
            }
        }
    }
    
    private Object processChildGroups(Matcher matcher, GroupDetails parentGroupDetails) {
        Map<String, Object> parsedGroups = new HashMap<String, Object>();
        
        for (GroupDetails childGroupDetails : parentGroupDetails.getChildGroups()) {
            String groupContent = matcher.group(childGroupDetails.getGroupName());

            if (childGroupDetails.isType(GroupType.LOOP)) {
                GroupDetails blockGroup = childGroupDetails.getBlockSubgroupForLoopGroup();
                Object result = processGroup(blockGroup, groupContent);
                parsedGroups.put(childGroupDetails.getGroupName(), result);
            } else {
                String groupContentInSingleLine = groupContent.replace("\n", "");
                log.info("extracted group {} : >>>{}<<<", childGroupDetails.getGroupName(), groupContentInSingleLine);
                parsedGroups.put(childGroupDetails.getGroupName(), groupContentInSingleLine);
            }
        }
        return parsedGroups;
    }
    
    private Matcher getMatcher(GroupDetails parentGroupDetails, String content) {
        String regexp = registry.getRegexByGroupName(parentGroupDetails.getGroupName());
        Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL | Pattern.MULTILINE);
        return pattern.matcher(new InterruptibleCharSequence(content));
    }

}
