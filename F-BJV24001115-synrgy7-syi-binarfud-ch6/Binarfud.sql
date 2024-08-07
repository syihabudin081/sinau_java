PGDMP     *                    |            binarfud    15.2    15.2 !               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                        0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            !           1262    18225    binarfud    DATABASE        CREATE DATABASE binarfud WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_Indonesia.1252';
    DROP DATABASE binarfud;
                postgres    false            �            1255    26587    calculate_total_price(uuid)    FUNCTION     �  CREATE FUNCTION public.calculate_total_price(order_id_in uuid) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
DECLARE
    total_price NUMERIC;
BEGIN
    SELECT SUM(od.quantity * p.price)
    INTO total_price
    FROM order_detail od
    INNER JOIN product p ON od.product_id = p.id
    WHERE od.order_id = order_id_in;

    RETURN COALESCE(total_price, 0); -- Jika tidak ada detail pesanan, kembalikan 0
END;
$$;
 >   DROP FUNCTION public.calculate_total_price(order_id_in uuid);
       public          postgres    false            �            1255    26588     calculate_total_price_proc(uuid) 	   PROCEDURE     �  CREATE PROCEDURE public.calculate_total_price_proc(IN order_id_in uuid, OUT total_price numeric)
    LANGUAGE plpgsql
    AS $$
BEGIN
    SELECT SUM(od.quantity * p.price)
    INTO total_price
    FROM order_detail od
    INNER JOIN product p ON od.product_id = p.id
    WHERE od.order_id = order_id_in;

    IF total_price IS NULL THEN
        total_price := 0;
    END IF;
END;
$$;
 `   DROP PROCEDURE public.calculate_total_price_proc(IN order_id_in uuid, OUT total_price numeric);
       public          postgres    false            �            1255    26591 .   count_merchants_by_location(character varying) 	   PROCEDURE       CREATE PROCEDURE public.count_merchants_by_location(IN locationname character varying, OUT merchantcount integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    SELECT COUNT(*) INTO merchantCount
    FROM Merchant
    WHERE merchant_location = locationName AND deleted = FALSE;
END;
$$;
 q   DROP PROCEDURE public.count_merchants_by_location(IN locationname character varying, OUT merchantcount integer);
       public          postgres    false            �            1259    26534    merchant    TABLE     �   CREATE TABLE public.merchant (
    deleted boolean NOT NULL,
    open boolean NOT NULL,
    id uuid NOT NULL,
    merchant_location character varying(255) NOT NULL,
    merchant_name character varying(255) NOT NULL
);
    DROP TABLE public.merchant;
       public         heap    postgres    false            "           0    0    COLUMN merchant.deleted    COMMENT     F   COMMENT ON COLUMN public.merchant.deleted IS 'Soft-delete indicator';
          public          postgres    false    214            �            1259    26541    order_detail    TABLE     �   CREATE TABLE public.order_detail (
    deleted boolean NOT NULL,
    quantity integer NOT NULL,
    total_price numeric(38,2) NOT NULL,
    id uuid NOT NULL,
    order_id uuid NOT NULL,
    product_id uuid NOT NULL
);
     DROP TABLE public.order_detail;
       public         heap    postgres    false            #           0    0    COLUMN order_detail.deleted    COMMENT     J   COMMENT ON COLUMN public.order_detail.deleted IS 'Soft-delete indicator';
          public          postgres    false    215            �            1259    26546    orders    TABLE     �   CREATE TABLE public.orders (
    completed boolean NOT NULL,
    deleted boolean NOT NULL,
    order_time timestamp(6) without time zone,
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    destination_address character varying(255) NOT NULL
);
    DROP TABLE public.orders;
       public         heap    postgres    false            $           0    0    COLUMN orders.deleted    COMMENT     D   COMMENT ON COLUMN public.orders.deleted IS 'Soft-delete indicator';
          public          postgres    false    216            �            1259    26551    product    TABLE     �   CREATE TABLE public.product (
    deleted boolean NOT NULL,
    price numeric(38,2) NOT NULL,
    id uuid NOT NULL,
    merchant_id uuid NOT NULL,
    product_name character varying(255) NOT NULL
);
    DROP TABLE public.product;
       public         heap    postgres    false            %           0    0    COLUMN product.deleted    COMMENT     E   COMMENT ON COLUMN public.product.deleted IS 'Soft-delete indicator';
          public          postgres    false    217            �            1259    26556    users    TABLE     �   CREATE TABLE public.users (
    deleted boolean NOT NULL,
    id uuid NOT NULL,
    email_address character varying(255) NOT NULL,
    password character varying(255),
    username character varying(255) NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            &           0    0    COLUMN users.deleted    COMMENT     C   COMMENT ON COLUMN public.users.deleted IS 'Soft-delete indicator';
          public          postgres    false    218                      0    26534    merchant 
   TABLE DATA           W   COPY public.merchant (deleted, open, id, merchant_location, merchant_name) FROM stdin;
    public          postgres    false    214   S)                 0    26541    order_detail 
   TABLE DATA           `   COPY public.order_detail (deleted, quantity, total_price, id, order_id, product_id) FROM stdin;
    public          postgres    false    215   B*                 0    26546    orders 
   TABLE DATA           b   COPY public.orders (completed, deleted, order_time, id, user_id, destination_address) FROM stdin;
    public          postgres    false    216   �*                 0    26551    product 
   TABLE DATA           P   COPY public.product (deleted, price, id, merchant_id, product_name) FROM stdin;
    public          postgres    false    217   `+                 0    26556    users 
   TABLE DATA           O   COPY public.users (deleted, id, email_address, password, username) FROM stdin;
    public          postgres    false    218   ,       x           2606    26540    merchant merchant_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.merchant DROP CONSTRAINT merchant_pkey;
       public            postgres    false    214            z           2606    26545    order_detail order_detail_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT order_detail_pkey;
       public            postgres    false    215            |           2606    26550    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public            postgres    false    216            ~           2606    26555    product product_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.product DROP CONSTRAINT product_pkey;
       public            postgres    false    217            �           2606    26564    users users_email_address_key 
   CONSTRAINT     a   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_address_key UNIQUE (email_address);
 G   ALTER TABLE ONLY public.users DROP CONSTRAINT users_email_address_key;
       public            postgres    false    218            �           2606    26562    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    218            �           2606    26566    users users_username_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);
 B   ALTER TABLE ONLY public.users DROP CONSTRAINT users_username_key;
       public            postgres    false    218            �           2606    26577 "   orders fk32ql8ubntj5uh44ph9659tiih    FK CONSTRAINT     �   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);
 L   ALTER TABLE ONLY public.orders DROP CONSTRAINT fk32ql8ubntj5uh44ph9659tiih;
       public          postgres    false    216    3202    218            �           2606    26572 (   order_detail fkb8bg2bkty0oksa3wiq5mp5qnc    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkb8bg2bkty0oksa3wiq5mp5qnc FOREIGN KEY (product_id) REFERENCES public.product(id);
 R   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT fkb8bg2bkty0oksa3wiq5mp5qnc;
       public          postgres    false    215    3198    217            �           2606    26582 #   product fkk47qmalv2pg906heielteubu7    FK CONSTRAINT     �   ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkk47qmalv2pg906heielteubu7 FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);
 M   ALTER TABLE ONLY public.product DROP CONSTRAINT fkk47qmalv2pg906heielteubu7;
       public          postgres    false    214    217    3192            �           2606    26567 (   order_detail fkrws2q0si6oyd6il8gqe2aennc    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkrws2q0si6oyd6il8gqe2aennc FOREIGN KEY (order_id) REFERENCES public.orders(id);
 R   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT fkrws2q0si6oyd6il8gqe2aennc;
       public          postgres    false    215    3196    216               �   x�E�?O�0����،��q��8�@����8�SU�ZtW�?U�2yzޟ[�v�A��@���*U��-D(ؽ��+w�s�6drÐA$F@�Ѩ�샭Mt�txeYx�ms�YQ��6mNQ���*��Lt���玜�u��B����8`�h�5a��C�^x���ݕ��>\y��C�D�I���� 
	�b	rU2̱�sG�<}/�����2�c�����Y�T}         f   x�%��� �7�"�X$r��A�!�U7��SP�_�jY�����Ӟ2؏p��	�XQ2���-����c2�4��1��4�z�9�k�F���Iތ��j�/��         �   x��ͽ1@�:�"��8v� 1M~8��R"��I�r/GH(�1�Xv��0�S��٠��SEi�ZKɩ}��J"��U��K��1+���g{��.��_n����V(�Zݜ͆H9��P)L�jsq�����{�Z[;L         �   x���1�0 �����8u�fFbca`bq\G�@���d��t�I��ΘR�<[�� �i��PH	&֜���r ������Kt����Vf2
����p�v{�k��a���F�P!�2U�P�dh�h��bn����o����c��|2:�         I   x�K�L�405�4е425�5I50ҵ�47�MKJ�LM2M51�4�L,N-pH�M���K���442q��qqq U��     