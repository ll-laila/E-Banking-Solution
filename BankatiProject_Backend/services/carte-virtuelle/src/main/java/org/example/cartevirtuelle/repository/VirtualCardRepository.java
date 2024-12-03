package org.example.cartevirtuelle.repository;
import org.example.cartevirtuelle.entity.VirtualCard;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirtualCardRepository extends JpaRepository<VirtualCard, Long> {
    VirtualCard findByCardNumber(String cardNumber);
    List<VirtualCard> findByUserId(Long userId);
}
