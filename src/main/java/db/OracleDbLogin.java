package db;

public class OracleDbLogin {

    private String ServerName;
    private String PortaName;
    private String DbName;
    private String username;
    private String password;

    public OracleDbLogin() {
    }

    public OracleDbLogin(String ServerName, String PortaName, String DbName, String username, String password) {
        this.ServerName = ServerName;
        this.PortaName = PortaName;
        this.DbName = DbName;
        this.username = username;
        this.password = password;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public String getPortaName() {
        return PortaName;
    }

    public void setPortaName(String portaName) {
        PortaName = portaName;
    }

    public String getDbName() {
        return DbName;
    }

    public void setDbName(String dbName) {
        DbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
