package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.controller.mapper.LoanMapper;
import edu.eci.dosw.tdd.core.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper loanMapper;

    public LoanController(LoanService loanService, LoanMapper loanMapper) {
        this.loanService = loanService;
        this.loanMapper = loanMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanDTO createLoan(@RequestBody LoanDTO dto) {
        return loanMapper.toDTO(loanService.createLoan(dto.getUserId(), dto.getBookId()));
    }

    @PostMapping("/{bookId}/return")
    public LoanDTO returnLoan(@PathVariable String bookId, @RequestParam String userId) {
        return loanMapper.toDTO(loanService.returnLoan(userId, bookId));
    }

    @GetMapping("/user/{userId}")
    public List<LoanDTO> getLoansByUser(@PathVariable String userId) {
        return loanService.getLoansByUser(userId).stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());
    }
}