package app;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping
    public Double getDiscount(@RequestParam Gender gender, @RequestParam Integer age) {
        return discountService.getDiscount(gender, age);
    }

    @GetMapping("/allowance")
    public Double getAllowance(@RequestParam Gender gender, @RequestParam Integer age) {
        return discountService.getAllowance(gender, age);
    }
}
