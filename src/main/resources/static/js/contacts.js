console.log("Contacts.js");
const baseURL = "http://localhost:8081";
// const baseURL = "https://www.scm20.site";
const viewContactModal = document.getElementById("view_contact_modal");
const viewQueryModal = document.getElementById("view_query_modal");

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
      if (contactModal) {
        contactModal.classList.add("scale-100");
      }else{
        queryModal.classList.add("scale-100");
      }
    }, 50);
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "view_contact_modal",
  override: true,
};
const instanceQueryOptions = {
  id: "view_query_modal",
  override: true,
};

// Assuming Modal is a predefined class (or a UI library class)
const contactModal = new Modal(viewContactModal, options, instanceOptions);
const queryModal = new Modal(viewQueryModal, options, instanceQueryOptions);

function openContactModal() {
  contactModal.show();
}

function closeContactModal() {
  contactModal.hide();
}

function openQueryModal() {
  queryModal.show();
}

function closeQueryModel() {
  queryModal.hide();
}

async function loadContactdata(id) {
  //function call to load data
  console.log(id);
  try {
    const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
    console.log(data);
    if (data.name){
      console.log("loadContant data is null")
    }
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
  const title = `Are you sure you want to remove this User : ` + email;
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

async function openDescription(id) {
  console.log("Query Id: " + id);

  try {
    const queryData = await (await fetch(`${baseURL}/api/queries/${id}`)).json();
    console.log(queryData);

    // Check if the fetched data is valid
    if (!queryData || !queryData.id) {
      console.error("Invalid data for query", id);
      return;
    }
    // Populate the modal with the fetched data
    document.querySelector("#query_id").textContent = queryData.id;
    document.querySelector("#image_url").href = queryData.image;
    document.querySelector("#query_title").textContent = queryData.title || "No title available";
    document.querySelector("#query_description").textContent = queryData.content || "No description available";
    document.querySelector("#query_creator_name").textContent = queryData.name || "Unknown";
    // document.querySelector("#query_creator_realName").textContent = queryData.user || "Unknown";
    // document.querySelector("#query_creator_email").textContent = queryData.user || "Unknown";
    document.querySelector("#query_created_date").textContent = new Date(queryData.date).toLocaleDateString() || "Unknown";
    document.querySelector("#query_status").textContent = queryData.resolved ? "Resolved" : "Not Resolved";

    const queryImage = document.querySelector("#query_image");
    queryImage.src = queryData.image || 'default-image.jpg';

    openQueryModal();  // Open the modal after populating it
  } catch (error) {
    console.error("Error fetching query data: ", error);
  }
}

