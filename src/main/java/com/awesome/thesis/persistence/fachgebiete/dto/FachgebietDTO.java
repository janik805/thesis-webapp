package com.awesome.thesis.persistence.fachgebiete.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("fachgebiet")
public record FachgebietDTO (@Id String name){
}
