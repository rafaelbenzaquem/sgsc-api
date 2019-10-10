package com.rafaelbenz.sgsc.sgscapi.services.validation;

import com.rafaelbenz.sgsc.sgscapi.dto.ClienteNewDTO;
import com.rafaelbenz.sgsc.sgscapi.model.enums.TipoCliente;
import com.rafaelbenz.sgsc.sgscapi.resources.exception.FieldMessage;
import com.rafaelbenz.sgsc.sgscapi.services.validation.utils.CpfCnpjUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class TipoClienteValidator implements ConstraintValidator<TipoNovoCliente, ClienteNewDTO> {
    public void initialize(TipoNovoCliente constraint) {
    }

    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !CpfCnpjUtil.isValidCpf(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !CpfCnpjUtil.isValidCnpj(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
