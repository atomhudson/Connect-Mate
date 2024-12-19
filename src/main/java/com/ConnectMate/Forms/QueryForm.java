package com.ConnectMate.Forms;

import com.ConnectMate.Validators.ValidFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QueryForm {

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String name;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @ValidFile(message = "Invalid File")
    private MultipartFile image;
}
