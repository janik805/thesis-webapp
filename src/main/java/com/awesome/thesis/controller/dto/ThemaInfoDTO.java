package com.awesome.thesis.controller.dto;

import com.awesome.thesis.logic.domain.model.links.Link;

import java.util.List;

public record ThemaDTO(
        String titel,
        String beschreibung,
        List<Link> urls) {}
