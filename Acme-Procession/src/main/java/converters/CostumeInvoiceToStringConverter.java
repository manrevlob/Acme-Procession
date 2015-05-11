package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CostumeInvoice;

@Component
@Transactional
public class CostumeInvoiceToStringConverter implements Converter<CostumeInvoice, String> {

	@Override
	public String convert(CostumeInvoice entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
