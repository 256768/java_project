import java.util.HashMap;
import java.util.Map;

abstract class AbstractStudent {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected int birthYear;

    public AbstractStudent(int id, String firstName, String lastName, int birthYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    public abstract String getSkill();
}

class TelecomStudent extends Student {
    private static final Map<Character, String> morseCodeMap = new HashMap<>();

    static {
        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        morseCodeMap.put(' ', "/");
    }

    public TelecomStudent(int id, String type, String firstName, String lastName, int birthYear) {
        super(id, type,  firstName, lastName, birthYear);
    }

    @Override
    public String getSkill() {
        return convertToMorse(firstName + " " + lastName);
    }

    private String convertToMorse(String text) {
        StringBuilder morseCode = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            String morseChar = morseCodeMap.get(c);
            if (morseChar != null) {
                morseCode.append(morseChar).append(" ");
            }
        }
        return morseCode.toString().trim();
    }
}

class CyberSecurityStudent extends Student {
    public CyberSecurityStudent(int id, String type, String firstName, String lastName, int birthYear) {
        super(id, type, firstName, lastName, birthYear);
    }

    @Override
    public String getSkill() {
        return hashName(firstName + " " + lastName);
    }

    private String hashName(String name) {
        String fullname = firstName + " " + lastName;
        int hash = fullname.hashCode();
        return "hash:" + hash;
    }
}
