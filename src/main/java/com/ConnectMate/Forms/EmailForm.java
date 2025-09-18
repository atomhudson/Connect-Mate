package com.ConnectMate.Forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailForm {

    private String to;

    @NotBlank(message = "Subject Cannot Be Empty")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String subject;

    @NotBlank(message = "Body is required")
    private String mytextarea;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMytextarea() {
        return mytextarea;
    }

    public void setMytextarea(String mytextarea) {
        this.mytextarea = mytextarea;
    }
}
