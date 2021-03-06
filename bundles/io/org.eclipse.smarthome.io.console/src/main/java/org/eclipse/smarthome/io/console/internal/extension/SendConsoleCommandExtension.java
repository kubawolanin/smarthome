/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.io.console.internal.extension;

import java.util.Collections;
import java.util.List;

import org.eclipse.smarthome.core.events.EventPublisher;
import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.items.ItemNotFoundException;
import org.eclipse.smarthome.core.items.ItemNotUniqueException;
import org.eclipse.smarthome.core.items.ItemRegistry;
import org.eclipse.smarthome.core.items.events.ItemEventFactory;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.TypeParser;
import org.eclipse.smarthome.io.console.Console;
import org.eclipse.smarthome.io.console.extensions.AbstractConsoleCommandExtension;

/**
 * Console command extension to send command to item
 *
 * @author Kai Kreuzer - Initial contribution and API
 * @author Markus Rathgeb - Create DS for command extension
 * @author Dennis Nobel - Changed service references to be injected via DS
 * @author Stefan Bußweiler - Migration to new ESH event concept
 * 
 */
public class SendConsoleCommandExtension extends AbstractConsoleCommandExtension {

    private ItemRegistry itemRegistry;
    private EventPublisher eventPublisher;

    public SendConsoleCommandExtension() {
        super("send", "Send a command to an item.");
    }

    @Override
    public List<String> getUsages() {
        return Collections.singletonList(buildCommandUsage("<item> <command>", "sends a command for an item"));
    }

    @Override
    public void execute(String[] args, Console console) {
        if (args.length > 0) {
            String itemName = args[0];
            try {
                Item item = this.itemRegistry.getItemByPattern(itemName);
                if (args.length > 1) {
                    String commandName = args[1];
                    Command command = TypeParser.parseCommand(item.getAcceptedCommandTypes(), commandName);
                    if (command != null) {
                        eventPublisher.post(ItemEventFactory.createCommandEvent(itemName, command));
                        console.println("Command has been sent successfully.");
                    } else {
                        console.println("Error: Command '" + commandName + "' is not valid for item '" + itemName + "'");
                        console.print("Valid command types are: ( ");
                        for (Class<? extends Command> acceptedType : item.getAcceptedCommandTypes()) {
                            console.print(acceptedType.getSimpleName() + " ");
                        }
                        console.println(")");
                    }
                } else {
                    printUsage(console);
                }
            } catch (ItemNotFoundException e) {
                console.println("Error: Item '" + itemName + "' does not exist.");
            } catch (ItemNotUniqueException e) {
                console.print("Error: Multiple items match this pattern: ");
                for (Item item : e.getMatchingItems()) {
                    console.print(item.getName() + " ");
                }
            }
        } else {
            printUsage(console);
        }

    }

    protected void setItemRegistry(ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    protected void unsetItemRegistry(ItemRegistry itemRegistry) {
        this.itemRegistry = null;
    }

    protected void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    protected void unsetEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = null;
    }

}
