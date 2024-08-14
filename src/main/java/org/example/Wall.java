package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return searchBlockByColor(color, blocks);
    }

    private Optional<Block> searchBlockByColor(String color, List<Block> blocks) {
        for (Block block : blocks) {
            if (block.getColor().equals(color)) {
                return Optional.of(block);
            }
            if (block instanceof CompositeBlock) {
                Optional<Block> result = searchBlockByColor(color, ((CompositeBlock) block).getBlocks());
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> listOfMaterials = new ArrayList<>();
        collectBlocksByMaterial(material, blocks, listOfMaterials);
        return listOfMaterials;
    }

    private void collectBlocksByMaterial(String material, List<Block> blocks, List<Block> listOfMaterials) {
        for (Block block : blocks) {
            if (block.getMaterial().equals(material)) {
                listOfMaterials.add(block);
            }
            if (block instanceof CompositeBlock) {
                collectBlocksByMaterial(material, ((CompositeBlock) block).getBlocks(), listOfMaterials);
            }

        }
    }

    @Override
    public int count() {
        return countAllBlocks(blocks);
    }

    private int countAllBlocks(List<Block> blocks) {
        int total = 0;
        for (Block block : blocks) {
            total++;
            if (block instanceof CompositeBlock) {
                total += countAllBlocks(((CompositeBlock) block).getBlocks());
            }
        }
        return total;
    }
}