package sk2.User_servis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk2.User_servis.entities.CreditCard;
import sk2.User_servis.entities.User;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    CreditCard findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);

}
