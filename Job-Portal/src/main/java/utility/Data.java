package utility;

public class Data {
    public static String buildOtpEmail(String name, String otp) {
        int year = java.time.Year.now().getValue();
        return String.format("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8" />
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>Your One-Time Password</title>
                  <style>
                    body {
                      margin: 0;
                      padding: 0;
                      background-color: #f3f4f6;
                      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    }
                    .email-container {
                      max-width: 600px;
                      margin: 40px auto;
                      background-color: #ffffff;
                      border-radius: 12px;
                      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
                      overflow: hidden;
                    }
                    .email-header {
                      background-color: #4f46e5;
                      color: #ffffff;
                      text-align: center;
                      padding: 24px;
                    }
                    .email-header h1 {
                      margin: 0;
                      font-size: 24px;
                    }
                    .email-body {
                      padding: 30px 20px;
                      color: #333333;
                    }
                    .email-body h2 {
                      font-size: 20px;
                      margin-top: 0;
                    }
                    .otp-code {
                      margin: 30px auto;
                      background-color: #f1f5f9;
                      width: fit-content;
                      padding: 15px 30px;
                      font-size: 28px;
                      font-weight: bold;
                      letter-spacing: 8px;
                      color: #1e293b;
                      border-radius: 8px;
                      text-align: center;
                    }
                    .email-footer {
                      text-align: center;
                      padding: 20px;
                      font-size: 12px;
                      color: #94a3b8;
                      background-color: #f8fafc;
                    }
                    @media (max-width: 640px) {
                      .email-container {
                        margin: 10px;
                      }
                      .otp-code {
                        font-size: 24px;
                        padding: 12px 24px;
                      }
                    }
                  </style>
                </head>
                <body>
                  <div class="email-container">
                    <div class="email-header">
                      <h1>Your OTP Code</h1>
                    </div>
                    <div class="email-body">
                      <h2>Hello, %s</h2>
                      <p>
                        You (or someone else) requested a one-time password (OTP) for a secure action in your account.
                        Please use the code below to proceed. It is valid for the next 10 minutes.
                      </p>
                      <div class="otp-code">%s</div>
                      <p>If you didn’t request this, please ignore this email. No further action is required.</p>
                      <p>Thanks,<br />The Team</p>
                    </div>
                    <div class="email-footer">
                      &copy;%d Your Company Name. All rights reserved.
                    </div>
                  </div>
                </body>
                </html>
                """, name, otp, year);
    }
}
