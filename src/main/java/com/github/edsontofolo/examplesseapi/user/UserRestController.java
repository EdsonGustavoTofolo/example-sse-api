package com.github.edsontofolo.examplesseapi.user;

import com.github.edsontofolo.examplesseapi.dto.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public void postMessage(@RequestBody MessageRequest messageRequest) {
        userService.postMessage(messageRequest.getUser(), messageRequest.getMessage());
    }
}
