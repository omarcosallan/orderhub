package com.omarcosallan.orderhub.entity.pk;

import com.omarcosallan.orderhub.entity.Item;
import com.omarcosallan.orderhub.entity.Sale;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SaleItemPK {

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
