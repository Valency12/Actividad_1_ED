package edu.app.listas;

public class DataTypeExamples {
    // Clase anidada Contact
    public static class Contact {
        private String name;
        private String address;
        private String phone;

        public Contact(String name, String address, String phone) {
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        // Getters y Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        @Override
        public String toString() {
            return String.format("%s (%s) - %s", name, phone, address);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Contact other = (Contact) obj;
            return this.name.equalsIgnoreCase(other.name)
                    && this.phone.equalsIgnoreCase(other.phone);
        }

        @Override
        public int hashCode() {
            return (name.toLowerCase() + phone).hashCode();
        }
    }
}
