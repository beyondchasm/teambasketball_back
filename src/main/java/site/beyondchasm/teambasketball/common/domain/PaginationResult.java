package site.beyondchasm.teambasketball.common.domain;

import java.util.List;

import lombok.Data;

@Data
public class PaginationResult<T> {

  private List<T> data;
  private int page;
  private int pageSize;
  private long totalElements;

}