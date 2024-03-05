package main.sulsul.admin.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
public class PageResultDto<EN, DTO> {

    List<DTO> result;

    private int currentPage;

    private int size;

    private long total;

    private int startPage;

    private int endPage;

    private boolean isPrev;

    private boolean isNext;

    public PageResultDto(Page<EN> page, List<DTO> dtoList) {

        this.result = dtoList;
        this.total = page.getTotalElements();

        makePageList(page.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.currentPage = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가

        this.size = pageable.getPageSize();

        this.endPage = (int) (Math.ceil(this.currentPage / 10.0)) * 10;

        this.startPage = this.endPage - 9;

        int last = (int) (Math.ceil((total) / (double) size));

        this.endPage = endPage > last ? last : endPage;

        this.isPrev = this.currentPage > 1;

        this.isNext = currentPage < last;
    }
}
