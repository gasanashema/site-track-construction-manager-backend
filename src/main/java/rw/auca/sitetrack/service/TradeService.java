package rw.auca.sitetrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.sitetrack.model.Trade;
import rw.auca.sitetrack.repository.TradeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public String save(Trade trade) {
        tradeRepository.save(trade);
        return "Trade saved successfully";
    }

    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getById(UUID id) {
        return tradeRepository.findById(id);
    }

    public String update(UUID id, Trade trade) {
        if (!tradeRepository.existsById(id)) {
            return "The trade with that id does not exist";
        }
        trade.setId(id);
        tradeRepository.save(trade);
        return "Trade updated successfully";
    }

    public String delete(UUID id) {
        if (!tradeRepository.existsById(id)) {
            return "The trade with that id does not exist";
        }
        tradeRepository.deleteById(id);
        return "Trade deleted successfully";
    }
}
