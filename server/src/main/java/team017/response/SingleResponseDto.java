package team017.response;

import lombok.Getter;

@Getter
public class SingleResponseDto<T> {
    private T data;
}
