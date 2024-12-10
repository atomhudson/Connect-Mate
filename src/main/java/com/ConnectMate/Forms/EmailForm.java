package com.ConnectMate.Forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmailForm {

    private String to;

    @NotBlank(message = "Subject Cannot Be Empty")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String subject;

    @NotBlank(message = "Body is required")
    private String mytextarea;

}
