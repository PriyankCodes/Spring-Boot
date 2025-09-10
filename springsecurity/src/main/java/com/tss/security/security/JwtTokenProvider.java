package com.tss.security.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tss.security.exception.UserApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SecurityException;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-private-key}")
	private String jwtPrivateKey;

	@Value("${app.jwt-public-key}")
	private String jwtPublicKey;

	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	private PrivateKey getPrivateKey() {
		try {
			byte[] keyBytes = Decoders.BASE64.decode(jwtPrivateKey);
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(spec);
		} catch (Exception e) {
			throw new UserApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading private key: " + e.getMessage());
		}
	}

	private PublicKey getPublicKey() {
		try {
			byte[] keyBytes = Decoders.BASE64.decode(jwtPublicKey);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(spec);
		} catch (Exception e) {
			throw new UserApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading public key: " + e.getMessage());
		}
	}
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
				.claims()
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(expireDate)
				.and()
				.signWith(getPrivateKey())
				.claim("role", authentication.getAuthorities())
				.compact();
		
		return token;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getPublicKey()).build().parse(token);
			return true;
		} catch (MalformedJwtException ex) {
			throw new UserApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new UserApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new UserApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new UserApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
		} catch (SecurityException ex) {
			throw new UserApiException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
		}
	}
	
	public String getUsername(String token) {
		
		Claims claims = Jwts.parser().verifyWith(getPublicKey()).build().parseSignedClaims(token).getPayload();
		String username = claims.getSubject();
		
		return username;
	}
}




//package com.tss.bank.security;
//
//import java.util.Date;
//
//import javax.crypto.SecretKey;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import com.tss.bank.exception.UserApiException;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//
//
//@Component
//public class JwtTokenProvider {
//
//  @Value("${app.jwt-secret}")
//  private String jwtSecret;
//
//  @Value("${app-jwt-expiration-milliseconds}")
//  private long jwtExpirationDate;
//
//  private SecretKey key() {
//      return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//  }
//  
//  public String generateToken(Authentication authentication) {
//      String username = authentication.getName();
//      
//      Date currentDate = new Date();
//      Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
//      
//      String token = Jwts.builder()
//              .claims()
//              .subject(username)
//              .issuedAt(new Date(System.currentTimeMillis()))
//              .expiration(expireDate)
//              .and()
//              .signWith(key())
//              .claim("role", authentication.getAuthorities())
//              .compact();
//      
//      return token;
//  }
//  
//  public boolean validateToken(String token) {
//      try {
//          Jwts.parser().verifyWith(key()).build().parse(token);
//          return true;
//      } catch (MalformedJwtException ex) {
//          throw new UserApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
//      } catch (ExpiredJwtException ex) {
//          throw new UserApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
//      } catch (UnsupportedJwtException ex) {
//          throw new UserApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
//      } catch (IllegalArgumentException ex) {
//          throw new UserApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
//      } catch (SecurityException ex) {
//          throw new UserApiException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
//      }
//  }
//  
//  public String getUsername(String token) {
//      
//      Claims claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
//      String username = claims.getSubject();
//      
//      return username;
//  }
//}
