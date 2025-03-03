package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.MailBody;

public interface EmailService {

    void sendSimpleMessage(MailBody mailBody);
}
