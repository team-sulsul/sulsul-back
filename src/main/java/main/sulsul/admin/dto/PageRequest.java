package main.sulsul.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PageRequest {

    private int page;

    private int size;

    public Pageable pageable() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size);
    }
}
