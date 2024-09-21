package com.blog.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.blog.utils.constants.APIPathConstant.V1_BLOG_BASE_PATH;

@Tag(name = "Blog" ,description = "User version 1 API for blog application.")
@RestController
@RequestMapping(V1_BLOG_BASE_PATH)
public class BlogController {
}
