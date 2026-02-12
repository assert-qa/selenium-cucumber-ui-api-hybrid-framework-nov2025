package pages.models;

public class ContactFormData {

    private String name;
    private String email;
    private String subject;
    private String message;

    private ContactFormData(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.subject = builder.subject;
        this.message = builder.message;
    }

    public ContactFormData() {

    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private String subject;
        private String message;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ContactFormData build() {
            return new ContactFormData(this);
        }
    }
}
