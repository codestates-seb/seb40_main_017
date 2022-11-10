package team017.global.response;

import lombok.Getter;

@Getter
public class SingleResponseDto<T> {
    private T data;
}
