package com.example.parkingpal;

//User class stores User information when they create an account.
public class Users{
    private int ID;
    private Info info;

    public Users(){
        ID = 99;
        info = new Info();
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public Info getInfo(){
        return info;
    }

    public void setInfo(Info info){
        this.info = info;
    }

    //Nested class so that individual info can be added to database.
    public static class Info{
        private String location;
        private String name;
        private String pass;
        private String email;
        private String university;
        private String passExpiration;
        private String password;
//        private String permit;

        public Info() {
            location = "Default";
            name = "Default";
            pass = "Default";
            email = "Default";
            university = "Default";
            passExpiration = "Default";
            password = "Default";
//            permit = "Default";
        }

        public Info(String location,String name,String pass, String email, String university, String passExpiration, String password) {
            this.location = location;
            this.name = name;
            this.pass = pass;
            this.email = email;
            this.university = university;
            this.passExpiration = passExpiration;
            this.password = password;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public String getPassExpiration() {
            return passExpiration;
        }

        public void setPassExpiration(String passExpiration) {
            this.passExpiration = passExpiration;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


}
