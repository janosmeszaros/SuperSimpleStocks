package com.assignment.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.util.Optional;

public class OptionalTypeAdapter extends XmlAdapter<BigDecimal, Optional<BigDecimal>> {
    @Override
    public Optional<BigDecimal> unmarshal(BigDecimal v) throws Exception {
        return Optional.ofNullable(v);
    }

    @Override
    public BigDecimal marshal(Optional<BigDecimal> v) throws Exception {
        return v.orElse(null);
    }
}
