console.log("Contacts.js");
const baseURL = "http://localhost:8081";
// const baseURL = "https://www.scm20.site";
const viewContactModal = document.getElementById("view_contact_modal");
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    setTimeout(() => {
      contactModal.classList.add("scale-100");
    }, 50);
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "view_contact_mdoal",
  override: true,
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
  contactModal.show();
}

function closeContactModal() {
  contactModal.hide();
}

async function loadContactdata(id) {
  //function call to load data
  console.log(id);
  try {
    const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
    console.log(data);
    document.querySelector("#contact_name").innerHTML = data.name;
    document.querySelector("#contact_email").innerHTML = data.email;
    document.querySelector("#contact_image").src = data.picture;
    document.querySelector("#contact_address").innerHTML = data.address;
    document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
    document.querySelector("#contact_about").innerHTML = data.description;
    const contactFavorite = document.querySelector("#contact_favorite");
    if (data.favorite) {
      contactFavorite.innerHTML =
        "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
    } else {
      contactFavorite.innerHTML = "Not Favorite Contact";
    }

    document.querySelector("#contact_website").href = data.websiteLink;
    document.querySelector("#contact_website").innerHTML = data.websiteLink;
    document.querySelector("#contact_linkedIn").href = data.linkedInLink;
    document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
    openContactModal();
  } catch (error) {
    console.log("Error: ", error);
  }
}

// delete contact

async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to delete the contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
    customClass: {
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
      const url = `${baseURL}/user/contacts/delete/` + id;
      window.location.replace(url);
    }
  });
}

async function isEnabled(email, status) {
  const action = status ? "disable" : "enable";
  const title = `Do you want to ${action} this user?`;

  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/isEnabled/` + email;
      window.location.replace(url);
    }
  });
}

async function isEmailVerified(email, status) {
  const action = status ? "mark as not verified" : "verify";
  const title = `Are you sure you want to ${action} this user's email?`;


  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/isEmailVerified/` + email;
      window.location.replace(url);
    }
  });
}

async function isPhoneVerified(email, status) {
  const action = status ? "mark the phone number as unverified" : "verify the phone number";
  const title = `Are you sure you want to ${action}?`;
  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/isPhoneVerified/` + email;
      window.location.replace(url);
    }
  });
}
async function deleteUser(email) {
  const title = `Are you sure you want to remove this User : `+email;
  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/deleteUser/` + email;
      window.location.replace(url);
    }
  });
}