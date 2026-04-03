package edu.eci.dosw.tdd.persistence.mapper;

import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.LoanStatus;
import edu.eci.dosw.tdd.persistence.dao.LoanEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanPersistenceMapper {

    private final UserPersistenceMapper userMapper;
    private final BookPersistenceMapper bookMapper;

    public LoanPersistenceMapper(UserPersistenceMapper userMapper, BookPersistenceMapper bookMapper) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public Loan toModel(LoanEntity entity) {
        Loan loan = new Loan();
        loan.setBook(bookMapper.toModel(entity.getBook()));
        loan.setUser(userMapper.toModel(entity.getUser()));
        loan.setLoanDate(entity.getLoanDate());
        loan.setReturnDate(entity.getReturnDate());
        loan.setStatus(LoanStatus.valueOf(entity.getStatus()));
        return loan;
    }
}