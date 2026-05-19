package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private final PrivateKey privateKey;

    private final PublicKey publicKey;

    public JwtService(@Value("${jwt.private.key}") String privateKeyStr,
                      @Value("${jwt.public.key}") String publicKeyStr) {
        try{
            this.privateKey = loadPrivateKey(privateKeyStr);
            this.publicKey = loadPublicKey(publicKeyStr);
        } catch (Exception e) {
            throw new RuntimeException("Error loading RSA Keys",e);
        }
    }

    public String generateToken(String email,String role){
        return Jwts.builder()
                .subject(email)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(privateKey,Jwts.SIG.RS256)
                .compact();
    }

    public String extractUseremail(String token){
        return getClaims(token).getSubject();
    }

    public String extractRole(String token){
        return getClaims(token).get("role",String.class);
    }


    public boolean isValid(String token){
        try{
            getClaims(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        String cleanedkey=privateKeyStr.replace("\\s","");
        byte[] decoded= Base64.getDecoder().decode(cleanedkey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        String cleanedKey=publicKeyStr.replace("\\s","");
        byte[] decoded= Base64.getDecoder().decode(cleanedKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

}
