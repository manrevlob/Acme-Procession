package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RegistrationInvoice;

@Component
@Transactional
public class RegistrationInvoiceToStringConverter implements Converter<RegistrationInvoice, String> {

	@Override
	public String convert(RegistrationInvoice entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
