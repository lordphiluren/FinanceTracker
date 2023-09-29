package com.lordphiluren.financetracker.factory;

import com.lordphiluren.financetracker.dto.AccountDTO;
import com.lordphiluren.financetracker.dto.CategoryDTO;
import com.lordphiluren.financetracker.dto.FinanceOperationDTO;
import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.Category;
import com.lordphiluren.financetracker.models.FinanceOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelsDTOFactory {
    private final ModelMapper modelMapper;
    @Autowired
    public ModelsDTOFactory(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FinanceOperationDTO makeFinanceOperationDTO(FinanceOperation financeOperation) {
        FinanceOperationDTO financeOperationDTO = modelMapper.map(financeOperation, FinanceOperationDTO.class);
        financeOperationDTO.setCategoryId(financeOperation.getCategory().getId());
        financeOperationDTO.setAccountId(financeOperation.getAccount().getId());
        return financeOperationDTO;
    }

    public FinanceOperation makeFinanceOperation(FinanceOperationDTO financeOperationDTO) {
        return modelMapper.map(financeOperationDTO, FinanceOperation.class);
    }
    public CategoryDTO makeCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
    public Category makeCategory(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public Account makeAccount(AccountDTO accountDTO) {
        return  modelMapper.map(accountDTO, Account.class);
    }
    public AccountDTO makeAccountDTO(Account account) {
        return  modelMapper.map(account, AccountDTO.class);
    }
}
