package com.onlinepharma.onlinepharma.controller;

import com.onlinepharma.onlinepharma.service.auth.BaseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractController<S extends BaseService> {

    protected final String API = "/api";
    protected final String VERSION = "/v1";

    protected final String PATH = API + VERSION;

    protected final S service;

}
