package org.example.mySpringProj.domain.userDomain;

public enum Role {
        ADMIN("관리자"),
        OWNER("가게사장"),
        USER("일반사용자");

        private String label;
        private Role(String label) {
                this.label = label;
        }
        public String getLabel() {
                return this.label;
        }
}