package cl.nessfit.web.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 7124965512564802080L;
    @Id
    private String rut;
    @Column (name = "first_name")
    private String firstName;
    @Column (name = "last_name")
    private String lastName;
    private String phone;
    private String email;
    private int status;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Role role;
    /**
     * Method that gets the rut of a user.
     * @return Rut of a user.
     */
    public String getRut() {
        return rut;
    }
    /**
     * Method that sets the rut for a user.
     * @param rut New rut for the user.
     */
    public void setRut(String rut) {
        this.rut = rut;
    }
    /**
     * Method that gets the name of a user.
     * @return Name of a user.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Method that sets the name of a user.
     * @param firstName New name for the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Method that gets the last name of a user.
     * @return Last name of a user.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Method that sets the last name of a user.
     * @param lastName New last name for the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Method that gets the phone number of a user.
     * @return Phone number of a user.
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Method that sets the phone number of a user.
     * @param phone New phone number for the user.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Method that gets the email of a user.
     * @return Email of a user.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Method that sets the email of a user.
     * @param email New email for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Method that gets the status of a user.
     * @return Status of a user.
     */
    public int getStatus() {
        return status;
    }
    /**
     * Method that sets the status of a user.
     * @param status New status for the user.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * Method that gets the password of a user.
     * @return Password of a user.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Method that sets the password of a user.
     * @param password New password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Method that gets the role of a user.
     * @return Role of a user.
     */
    public Role getRole() {
        return role;
    }
    /**
     * Method that sets the role of a user.
     * @param role New role for the user.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}