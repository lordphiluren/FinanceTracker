package com.lordphiluren.financetracker.web.mappers;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.Category;
import com.lordphiluren.financetracker.models.FinanceOperation;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.web.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelsMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public ModelsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FinanceOperationDTO makeFinanceOperationDTO(FinanceOperation financeOperation) {
        FinanceOperationDTO financeOperationDTO = modelMapper.map(financeOperation, FinanceOperationDTO.class);
        financeOperationDTO.setCategoryDTO(makeCategoryDTO(financeOperation.getCategory()));
        financeOperationDTO.setAccountDTO(makeAccountDTO(financeOperation.getAccount()));
        return financeOperationDTO;
    }
    public FinanceOperation makeFinanceOperation(FinanceOperationDTO financeOperationDTO) {
        return modelMapper.map(financeOperationDTO, FinanceOperation.class);
    }

    public UserDTO makeUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    public User makeUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public CategoryDTO makeCategoryDTO(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
//        categoryDTO.setUserDTO(makeUserDTO(category.getCreator()));
        return categoryDTO;
    }
    public Category makeCategory(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public Account makeAccount(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        //account.setUser(makeUser(accountDTO.getUser()));
        return account;
    }
    public AccountDTO makeAccountDTO(Account account) {
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
       // accountDTO.setUser(makeUserDTO(account.getUser()));
        return accountDTO;
    }

    public User makeUser(AuthDTO authDTO) {
        return modelMapper.map(authDTO, User.class);
    }
    public AuthDTO makeAuthDTO(User user) {
        return modelMapper.map(user, AuthDTO.class);
    }
}
