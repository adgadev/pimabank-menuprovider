package com.grexdev.pimabank.menuprovider.parser.regex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NamedGroupRegistry {

    private static final String NAMED_GROUP_REGEXP = "\\?<(?<GROUPNAME>\\w+)>";

    private Map<String, String> namedRegexGroups = new HashMap<String, String>();
    
    public NamedGroupRegistry(Map<String, String> namedRegexGroups) {
        this.namedRegexGroups = namedRegexGroups;
    }

    public String getRegexByGroupName(String name) {
        return namedRegexGroups.get(name);
    }

    public GroupDetails getRootGroupDetails() {
        return new GroupDetails();
    }

    private List<GroupDetails> getChildGroups(GroupDetails parentGroupDetails) {
        String regex = namedRegexGroups.get(parentGroupDetails.getGroupName());
        log.info("Parent group = {}, Regexp = {}", parentGroupDetails, regex);

        Pattern pattern = Pattern.compile(NAMED_GROUP_REGEXP);
        Matcher matcher = pattern.matcher(regex);
        List<GroupDetails> groupsOneLevelHigher = new ArrayList<GroupDetails>();

        while (matcher.find()) {
            String groupName = matcher.group("GROUPNAME");
            GroupDetails groupDetails = new GroupDetails(groupName);
            log.info("groupName = {}", groupDetails);

            if (groupDetails.groupLevel == parentGroupDetails.getGroupLevel() + 1) {
                groupsOneLevelHigher.add(groupDetails);
            }
        }
        return groupsOneLevelHigher;
    }

    enum GroupType {
        NORMAL, LOOP, BLOCK
    };

    @Getter
    @ToString
    class GroupDetails {

        private static final String ROOT_GROUP_NAME = "ROOT0";

        private final String groupName;

        private final int groupLevel;

        private final GroupType groupType;

        private GroupDetails() {
            this.groupName = ROOT_GROUP_NAME;
            this.groupLevel = 0;
            this.groupType = GroupType.LOOP;
        }

        private GroupDetails(String groupName) {
            this.groupName = groupName;
            Character lastChar = groupName.charAt(groupName.length() - 1);
            this.groupLevel = Integer.parseInt(lastChar.toString());
            String groupNameWithoutNumber = this.groupName.substring(0, groupName.length() - 1);

            if (groupNameWithoutNumber.endsWith("LOOP")) {
                this.groupType = GroupType.LOOP;
            } else if (groupNameWithoutNumber.endsWith("BLOCK")) {
                this.groupType = GroupType.BLOCK;
            } else {
                this.groupType = GroupType.NORMAL;
            }
        }

        public List<GroupDetails> getChildGroups() {
            if (groupType != GroupType.NORMAL) {
                return NamedGroupRegistry.this.getChildGroups(this);
            } else {
                return Collections.<GroupDetails> emptyList();
            }
        }

        public GroupDetails getBlockSubgroupForLoopGroup() {
            if (isType(GroupType.LOOP)) {
                List<GroupDetails> childGroups = getChildGroups();
                if (childGroups.size() == 1) {
                    return childGroups.get(0);
                }
            }
            throw new RuntimeException("LOOP type group must have exactly one child group and this child group must be of BLOCK type : groupDetails=" + this);
        }

        public boolean isType(GroupType type) {
            return groupType == type;
        }
    }

}
