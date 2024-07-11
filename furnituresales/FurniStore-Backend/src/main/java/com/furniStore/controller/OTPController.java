package com.furniStore.controller;

import com.furniStore.service.EmailService;
import com.furniStore.service.OTPService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("otp-service")
@RequiredArgsConstructor
public class OTPController {

    @NonNull
    private OTPService otpService;

    @NonNull
    private EmailService emailService;

    @PostMapping("mail")
    @ResponseBody
    public void sendOTPToMail(@RequestBody final String email) throws MessagingException {
        int otp = otpService.generateOTP(email);
        emailService.sendOtpMessage(email, "OTP -SpringBoot", String.format("The OTP is %d. " +
                "please use this OTP to proceed with accessing the FurniStore ", otp));
    }

    @GetMapping("mail")
    @ResponseBody
    public boolean validateOtp(@RequestParam(value = "otp") final int otpnum, @RequestParam(value = "email") final String email) {
        try {
            Validate.isTrue(otpnum > 0, "The OTP number must not be negative or 0");
            Validate.notEmpty(email, "Email must not be empty!");
        } catch (final IllegalArgumentException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
            int serverOtp = otpService.getOtp(email);
            if (serverOtp == otpnum) {
                otpService.clearOTP(email);
                return true;
            } else {
                return false;
            }
    }
}
