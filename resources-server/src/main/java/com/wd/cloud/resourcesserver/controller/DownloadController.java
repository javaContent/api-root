package com.wd.cloud.resourcesserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    @GetMapping("/{filename}")
    public ResponseEntity downlowdFile(@PathVariable String filename){
        return null;
    }
}
