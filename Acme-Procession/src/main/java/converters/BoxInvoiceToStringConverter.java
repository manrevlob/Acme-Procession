package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BoxInvoice;

@Component
@Transactional
public class BoxInvoiceToStringConverter implements Converter<BoxInvoice, String> {

	@Override
	public String convert(BoxInvoice entity) {
		String result;

		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());

		return result;
	}

}
