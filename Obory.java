class TelecomStudent extends Student {
    public TelecomStudent(int id, String firstName, String lastName, int birthYear) {
        super(id, firstName, lastName, birthYear);
    }

    @Override
    public String getSkill() {
        return convertToMorse(firstName + " " + lastName);
    }

    private String convertToMorse(String text) {
        // prevod text->morse
        return "";
    }
}

class CyberSecurityStudent extends Student {
    public CyberSecurityStudent(int id, String firstName, String lastName, int birthYear) {
        super(id, firstName, lastName, birthYear);
    }

    @Override
    public String getSkill() {
        return hashName(firstName + " " + lastName);
    }

    private String hashName(String name) {
        // prevod text->hash
        return "";
    }
}
