package edu.praktikum.sprint7.models;

public class CourierCreds {


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String login;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

        private CourierCreds(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public static CourierCreds credsFromCourier(Courier courier) {
            return new CourierCreds(courier.getLogin(), courier.getPassword());
        }
    }


