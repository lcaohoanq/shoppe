package com.lcaohoanq.authservice.domains.mfa

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

@Component
class CodeGenerator(private val writer: QRCodeWriter) {

    fun generate(issuer: String, email: String, secret: String): BufferedImage {
        val uri = "otpauth://totp/$issuer:$email?secret=$secret&issuer=$issuer"
        val matrix = writer.encode(uri, BarcodeFormat.QR_CODE, 200, 200)

        return MatrixToImageWriter.toBufferedImage(matrix)
    }

}