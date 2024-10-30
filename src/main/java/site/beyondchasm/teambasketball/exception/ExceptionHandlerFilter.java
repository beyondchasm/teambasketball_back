package site.beyondchasm.teambasketball.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (CustomException ex) {
      setErrorResponse(ex.getErrorCode().getHttpStatus(), response, ex);
    } catch (ExpiredJwtException ex) { // 만료된 JWT 예외 처리
      setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
    } catch (Exception ex) {
      setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, ex);
    }
  }

  private void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex)
      throws IOException {
    logger.error("[ExceptionHandlerFilter] errMsg : " + ex.getMessage(), ex);

    response.setStatus(status.value());
    response.setContentType("application/json; charset=UTF-8");

    response.getWriter().write(new ErrorResponse(ex.getMessage()).convertToJson());
  }
}
