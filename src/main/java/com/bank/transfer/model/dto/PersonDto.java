package com.bank.transfer.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	@JsonFormat(pattern = "dd.MM.yyyy")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthdate;

	@Size(min = 8, max = 400)
	private String password;
}