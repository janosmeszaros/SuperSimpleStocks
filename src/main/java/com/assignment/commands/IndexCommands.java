package com.assignment.commands;

import com.assignment.calculators.IndexCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;

import java.math.BigDecimal;

public class IndexCommands implements CommandMarker {

    @Autowired
    private IndexCalculator indexCalculator;

    @CliAvailabilityIndicator({"index"})
    public boolean isIndexAvailable() {
        return true;
    }

    @CliCommand(value = "index", help = "Calculate the index for all of the shares!")
    public BigDecimal index() {
        return indexCalculator.calculateIndex();
    }

}
