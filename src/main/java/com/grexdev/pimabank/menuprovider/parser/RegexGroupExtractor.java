package com.grexdev.pimabank.menuprovider.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.grexdev.pimabank.menuprovider.parser.NamedGroupRegistry.GroupDetails;
import com.grexdev.pimabank.menuprovider.parser.NamedGroupRegistry.GroupType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RegexGroupExtractor {
 
    private final NamedGroupRegistry registry;
    
    public Object extractRegexGroupsValues(String content) {
        log.debug("Extracting regexp groups from content >>>{}<<<", content);
        Object rootGroupValue = processGroup(registry.getRootGroupDetails(), content);
        log.debug("Root group value >>>{}<<<", rootGroupValue);
        return rootGroupValue;
    }

    private Object processGroup(GroupDetails parentGroupDetails, String content) {
        log.debug("Processing group {}", parentGroupDetails);
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
        return pattern.matcher(content);
    }

}
