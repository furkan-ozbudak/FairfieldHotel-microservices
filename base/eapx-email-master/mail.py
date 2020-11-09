#!/usr/bin/env python3

import smtplib
from email.header import Header
from email.mime.text import MIMEText
sender = 'eaprojectsx@gmail.com'
authCode = 'ljvyjdhzvmfrwfof'
class MessageService:
    def sendEmailMessage(self, email, message):
        print ("sendEmailMessage, email:"+email+", message:"+message)
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj['From'] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header('EA', 'utf-8')
        try:
            smtpObj = smtplib.SMTP('smtp.gmail.com', 587)
            smtpObj.starttls()
            smtpObj.login(sender, authCode)
            smtpObj.sendmail(sender, [email], messageObj.as_string())
            print ("send mail success")
            return True
        except smtplib.SMTPException:
            print ("send mail failed!")
            return False

# ms = MessageService()
# ms.sendEmailMessage("bruce1988cn@gmail.com","EA Test")
