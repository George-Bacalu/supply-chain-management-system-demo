const newProductButton = document.querySelector("#new-product-btn");
const productList = document.querySelector("#product-list");
const productInput = document.querySelector("#product-name");
const productSelect = document.querySelector("#product-quantity");

let productName;
let productQuantity;
let newProduct;
let deleteProductButton;

newProductButton.addEventListener("click", () => {
    initializeProductValues();
    resetFields();
    if (!fieldsAreValid) return;

    createListElement();
    appendElementsToPage();

});

const initializeProductValues = () => {
    productName = productInput.value.trim();
    productQuantity = productSelect.value;
}

const resetFields = () => {
    productInput.value = "";
    productSelect.value = "";
}

const fieldsAreValid = () => {
    return productName.trim().length !== 0 && productQuantity.trim().length !== 0;
}

const createListElement = () => {
    newProduct = document.createElement("li");
    newProduct.className = "my-2";
    newProduct.textContent = `name: ${productName}, quantity: ${productQuantity}`;

    deleteProductButton = document.createElement("button");
    deleteProductButton.textContent = "Delete product";
    deleteProductButton.className = "btn btn-danger btn-sm mx-3";
    deleteProductButton.addEventListener("click", event => event.preventDefault());
}

const appendElementsToPage = () => {
    productList.append(newProduct);
    newProduct.append(deleteProductButton);
}

productList.addEventListener("click", event => {
    if (event.target.tagName !== "LI") return;
    event.target.remove();
});
