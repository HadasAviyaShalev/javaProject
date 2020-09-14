package classes;

public class friends {
    private int code;
    private String fullName;
    private String email;

    @Override
    public String toString() {
        return "full name: "+this.getFullName()+" email: "+this.getEmail();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName.trim();
    }

    public void setEmail(String email) {
       // if(email.charAt('@')!=0&&email.charAt('@')!=-1&&!email.endsWith("@"))
            this.email = email;
//        else{
//            throw new IllegalArgumentException("wrong email");
//        }
    }

    public int getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public friends(int code, String fullName, String email) {
        this.code = code;
        this.setFullName(fullName);
        this.setEmail(email);

    }

    public friends() {
    }
}
